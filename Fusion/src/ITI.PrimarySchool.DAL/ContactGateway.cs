using System.Collections.Generic;
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

        public ContactGateway(string connectionString)
        {
            _connectionString = connectionString;
        }

        public async Task<Result<int>> ReciveContactList(string name, string mail, string number)
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                var p = new DynamicParameters();
                p.Add("@firstName", name);
                p.Add("@mail", mail);
                p.Add("@number", number);
                p.Add("@ContactId", dbType: DbType.Int32, direction: ParameterDirection.Output);
                p.Add("@Status", dbType: DbType.Int32, direction: ParameterDirection.ReturnValue);
                await con.ExecuteAsync("iti.sContactCreate", p, commandType: CommandType.StoredProcedure);

                int status = p.Get<int>("@Status");
                if (status == 1) return Result.Failure<int>(Status.BadRequest, "A Conctact with this name already exists.");

                Debug.Assert(status == 0);
                return Result.Success(Status.Created, p.Get<int>("@ContactId"));
            }
        }

        public async Task<IEnumerable<ContactData>> ListAll()
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                return await con.QueryAsync<ContactData>(@"select ContactId, FirstName, LastName, Mail, PhoneNumber from iti.vContact");
            }
        }

        public async Task<Result<ContactData>> FindById(int ContactId)
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                ContactData sms = await con.QueryFirstOrDefaultAsync<ContactData>(
                   "select ContactId, FirstName, LastName, Mail, PhoneNumber, from iti.tContact where ContactId = ContactId",
                    new { ContactId = ContactId });

                if (sms == null) return Result.Failure<ContactData>(Status.NotFound, "Contact not found.");
                return Result.Success(sms);
            }
        }

        public async Task<Result<int>> AddContact(string firstName, string lastName, string mail, string phoneNumber, string token)
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                var p = new DynamicParameters();
                p.Add("@FirstName", firstName );
                p.Add("@LastName", lastName);
                p.Add("@Mail", lastName);
                p.Add("@PhoneNumber", phoneNumber);
                p.Add("@ContactId", dbType: DbType.Int32, direction: ParameterDirection.Output);
                p.Add("@Status", dbType: DbType.Int32, direction: ParameterDirection.ReturnValue);
                await con.ExecuteAsync("iti.sContactCreate", p, commandType: CommandType.StoredProcedure);

                int status = p.Get<int>("@Status");
                if (status == 1) return Result.Failure<int>(Status.BadRequest, "A Device with this name already exists.");

                Debug.Assert(status == 0);
                return Result.Success(Status.Created, p.Get<int>("@ContactId"));
            }
        }

        public async Task<Result<ContactData>> FindByNumber(string number)
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                ContactData contact = await con.QueryFirstOrDefaultAsync<ContactData>(
                     "select SMSId, DevicesId, UsersId, Extern, [Time], [Message],direction from iti.tSMS where Extern = Number",
                    new { Number = number });

                if (contact == null) return Result.Failure<ContactData>(Status.NotFound, "Contact not found.");
                return Result.Success(contact);
            }
        }

        public async Task Delete(int smsId)
        {
            using (SqlConnection con = new SqlConnection(_connectionString))
            {
                await con.ExecuteAsync("iti.sSMSDelete", new { SMSId = smsId }, commandType: CommandType.StoredProcedure);
            }
        }
    }
}
