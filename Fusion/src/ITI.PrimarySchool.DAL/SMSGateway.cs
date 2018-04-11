using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
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
                return await con.QueryAsync<SMSData>(@"select SMSId, DevicesId, UsersId, Extern, [Time], [Message], direction from iti.tSMS");
            }
        }

        public async Task<Result<SMSData>> FindById( int smsId )
        {
            using( SqlConnection con = new SqlConnection( _connectionString ) )
            {
                SMSData sms = await con.QueryFirstOrDefaultAsync<SMSData>(
                   "select SMSId, DevicesId, UsersId, Extern, [Time], [Message], direction from iti.tSMS where SMSId = smsId",
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
                    "select SMSId, DevicesId, UsersId, Extern, [Time], [Message],direction from iti.tSMS where Extern = Number",
                    new { Number = number } );
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
