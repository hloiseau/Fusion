using System;

namespace Fusion.DAL
{
    public class SMSData
    {
        public int SMSId { get; set; }

        public int DevicesId { get; set; }

        public int UsersId { get; set; }

        public string Extern { get; set; }
        
        public DateTime Time { get; set; }

        public string Message { get; set; }

        public byte[] Direction { get; set; }
    }
}
