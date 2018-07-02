using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Text;
using System.Threading.Tasks;
using Dapper;

namespace Fusion.DAL
{
    public class DeviceGateway
    {
        readonly string _connectionString;

        public DeviceGateway(string connectionString)
        {
            _connectionString = connectionString;
        }

        public async Task<Result<int>> AddDevice(string name)
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                var p = new DynamicParameters();
                p.Add("@Name", name);
                p.Add("@Type", "Mobile");
                p.Add("@Token", "none");
                p.Add("@DevicesId", dbType: DbType.Int32, direction: ParameterDirection.Output);
                p.Add("@Status", dbType: DbType.Int32, direction: ParameterDirection.ReturnValue);
                await con.ExecuteAsync("iti.sDevicesCreate", p, commandType: CommandType.StoredProcedure);

                int status = p.Get<int>("@Status");
                if (status == 1)
                    return Result.Failure<int>(Status.BadRequest, "A Device with this name already exists.");

                Debug.Assert(status == 0);
                return Result.Success(Status.Created, p.Get<int>("@DevicesId"));
            }
        }
    }
}
