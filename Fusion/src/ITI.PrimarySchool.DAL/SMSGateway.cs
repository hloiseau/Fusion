﻿using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Data.SqlTypes;
using System.Diagnostics;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using Dapper;

namespace Fusion.DAL
{
    public class SMSGateway
    {
        readonly string _connectionString;

        public SMSGateway( string connectionString )
        {
            _connectionString = connectionString;
        }

        public async Task<IEnumerable<SMSData>> ListAll()
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                return await con.QueryAsync<SMSData>(@"select SMSId, DevicesId, UsersId, Extern, [Time], [Message], direction from iti.tSMS  order by [Time]");
            }
        }

        public async Task<Result<SMSData>> FindById( int smsId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                SMSData sms = await con.QueryFirstOrDefaultAsync<SMSData>(
                   "select SMSId, DevicesId, UsersId, Extern, [Time], [Message], direction from iti.tSMS where SMSId = @smsId",
                    new { SMSId = smsId } );

                if (sms == null) return Result.Failure<SMSData>(Status.NotFound, "SMS not found.");
                return Result.Success(sms);
            }
        }
        
        public async Task<IEnumerable<SMSData>> FindByNumber( string number )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                return await con.QueryAsync<SMSData>(
                    "with bottom as(select top(10) SMSId, DevicesId, UsersId, Extern, [Time], [Message], direction from iti.tSMS where Extern = @Number order by [Time] desc) select * from bottom order by[Time]",
                    new { Number = number } );
            }
        }

        public async Task<Result<int>> AddSMS(int usersId, string Extern, DateTime date, string Message, bool direction)
        {
            string result = null;
            using (SqlConnection con = new SqlConnection(_connectionString))
            {

                string pattern = "^\\+\\d{2}";
                string replacement = "0";
                Regex rgx = new Regex(pattern);
                if (Extern != null)
                {
                    result = rgx.Replace(Extern, replacement);
                    result = Regex.Replace(result, @"\s+", "");
                }

                var p = new DynamicParameters();
                p.Add("@DevicesId", 0);
                p.Add("@UsersId", usersId);
                p.Add("@Extern", result);
                p.Add("@Time", date);
                p.Add("@Message", Message);
                p.Add("@direction", direction);
                p.Add("@SMSId", dbType: DbType.Int32, direction: ParameterDirection.Output);
                p.Add("@Status", dbType: DbType.Int32, direction: ParameterDirection.ReturnValue);
                await con.ExecuteAsync("iti.sSMSCreate", p, commandType: CommandType.StoredProcedure);

                int status = p.Get<int>("@Status");
                if (status == 1) return Result.Failure<int>(Status.BadRequest, "A Conctact with this name already exists.");

                Debug.Assert(status == 0);
                return Result.Success(Status.Created, p.Get<int>("@SMSId"));
            }
        }

        public async Task Delete( int smsId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                await con.ExecuteAsync( "iti.sSMSDelete", new { SMSId = smsId }, commandType: CommandType.StoredProcedure );
            }
        }
    }
}
