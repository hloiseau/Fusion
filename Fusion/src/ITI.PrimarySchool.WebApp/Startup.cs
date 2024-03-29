﻿using System.Security.Claims;
using System.Text;
using Fusion.DAL;
using Fusion.WebApp.Authentication;
using Fusion.WebApp.Services;
using Microsoft.AspNetCore.Authentication.OAuth;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.IdentityModel.Tokens;

namespace Fusion.WebApp
{
    public class Startup
    {
        public Startup( IConfiguration configuration )
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        // For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940
        public void ConfigureServices( IServiceCollection services )
        {
            services.AddOptions();
            services.AddCors();
            services.AddMvc();
            services.AddSingleton( _ => new DeviceGateway( Configuration["ConnectionStrings:FusionDB"] ) );
            services.AddSingleton( _ => new ContactGateway( Configuration["ConnectionStrings:FusionDB"] ) );
            services.AddSingleton( _ => new UserGateway( Configuration["ConnectionStrings:FusionDB"] ) );
            services.AddSingleton( _ => new SMSGateway( Configuration[ "ConnectionStrings:FusionDB" ] ) );
            services.AddSingleton<PasswordHasher>();
            services.AddSingleton<UserService>();
            services.AddSingleton<TokenService>();
            services.AddSingleton<GoogleAuthenticationManager>();
            services.AddSignalR();
            string secretKey = Configuration[ "JwtBearer:SigningKey" ];
            SymmetricSecurityKey signingKey = new SymmetricSecurityKey( Encoding.ASCII.GetBytes( secretKey ) );

            services.Configure<TokenProviderOptions>( o =>
            {
                o.Audience = Configuration[ "JwtBearer:Audience" ];
                o.Issuer = Configuration[ "JwtBearer:Issuer" ];
                o.SigningCredentials = new SigningCredentials( signingKey, SecurityAlgorithms.HmacSha256 );
            } );

            services.AddAuthentication(CookieAuthentication.AuthenticationScheme)
                .AddCookie(CookieAuthentication.AuthenticationScheme)
                .AddJwtBearer(JwtBearerAuthentication.AuthenticationScheme,
                o =>
                {
                    o.TokenValidationParameters = new TokenValidationParameters
                    {
                        ValidateIssuerSigningKey = true,
                        IssuerSigningKey = signingKey,

                        ValidateIssuer = true,
                        ValidIssuer = Configuration["JwtBearer:Issuer"],

                        ValidateAudience = true,
                        ValidAudience = Configuration["JwtBearer:Audience"],

                        NameClaimType = ClaimTypes.Email,
                        AuthenticationType = JwtBearerAuthentication.AuthenticationType
                    };
                })
                .AddGoogle(o =>
                {
                    o.SignInScheme = CookieAuthentication.AuthenticationScheme;
                    o.ClientId = Configuration["Authentication:Google:ClientId"];
                    o.ClientSecret = Configuration["Authentication:Google:ClientSecret"];
                    o.Events = new OAuthEvents
                    {
                        OnCreatingTicket = ctx =>
                            ctx.HttpContext.RequestServices.GetRequiredService<GoogleAuthenticationManager>()
                                .OnCreatingTicket(ctx)
                    };
                    o.AccessType = "offline";
                });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure( IApplicationBuilder app, IHostingEnvironment env )
        {
            if( env.IsDevelopment() )
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseCors(builder =>
                builder.AllowAnyOrigin()
                    .AllowAnyMethod()
                    .AllowAnyHeader());
            string secretKey = Configuration[ "JwtBearer:SigningKey" ];
            SymmetricSecurityKey signingKey = new SymmetricSecurityKey( Encoding.ASCII.GetBytes( secretKey ) );

            app.UseAuthentication();

            app.UseSignalR(routes =>
            {
                routes.MapHub<VueHub>("/Vue");
            });

            app.UseMvc( routes =>
            {
                routes.MapRoute(    
                    name: "default",
                    template: "{controller}/{action}/{id?}",
                    defaults: new { controller = "Home", action = "Index" } );

                routes.MapRoute(
                    name: "spa-fallback",
                    template: "Home/{*anything}",
                    defaults: new { controller = "Home", action = "Index" } );
            } );

            app.UseStaticFiles();
        }
    }
}
