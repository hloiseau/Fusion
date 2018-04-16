﻿using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Threading.Tasks;
using Dapper;

namespace Fusion.DAL
{
    public class ContactGateway
    {
        readonly string _connectionString;

        public ContactGateway( string connectionString )
        {
            _connectionString = connectionString;
        }

        public async Task<IEnumerable<ContactData>> ListAll()
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                return await con.QueryAsync<ContactData>(@"select SMSId, DevicesId, UsersId, Extern, [Time], [Message], direction from iti.tSMS");
            }
        }

        public async Task<Result<ContactData>> FindById( int smsId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                ContactData sms = await con.QueryFirstOrDefaultAsync<ContactData>(
                   "select SMSId, DevicesId, UsersId, Extern, [Time], [Message], direction from iti.tSMS where SMSId = smsId",
                    new { SMSId = smsId } );

                if (sms == null) return Result.Failure<ContactData>(Status.NotFound, "SMS not found.");
                return Result.Success(sms);
            }
        }
        
        public async Task<Result<ContactData>> FindByNumber( string number )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                ContactData contact = await con.QueryFirstOrDefaultAsync<ContactData>(
                     "select SMSId, DevicesId, UsersId, Extern, [Time], [Message],direction from iti.tSMS where Extern = Number",
                    new { Number = number } );

                if (contact == null) return Result.Failure<ContactData>(Status.NotFound, "Contact not found.");
                return Result.Success(contact);
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