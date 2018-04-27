﻿using System;
using Microsoft.AspNetCore.Mvc;

namespace Fusion.WebApp.Controllers
{
    public class ActionResultOptions<T>
    {
        public ActionResultOptions( Controller controller )
        {
            if( controller == null ) throw new ArgumentNullException( nameof( controller ) );
            Controller = controller;
        }

        public string RouteName { get; set; }

        public Func<T, object> RouteValues { get; set; }

        public Controller Controller { get; }
    }
}