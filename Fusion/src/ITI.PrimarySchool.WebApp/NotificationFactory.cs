using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace Fusion.WebApp
{
    public class NotificationFactory
    {
        public static String SendNotificationFromFirebaseCloud(string title, string text)
        {
            var result = "-1";
            var webAddr = "https://fcm.googleapis.com/fcm/send";
            var httpWebRequest = (HttpWebRequest)WebRequest.Create(webAddr);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Headers.Add(HttpRequestHeader.Authorization,"key=AIzaSyA_EhUsqUvC_1TwgqXTLzX5TEM9R08fhzE");
            httpWebRequest.Method = "POST";
            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
               
                string strNJson =
                    @"{
                    ""to"": ""/topics/ServiceNow"",                  
                    ""data"": {
                        ""title"": ""@title"",
                        ""text"": ""@text"",
                        ""sound"":""default""
                    }
                }";
                StringBuilder builder = new StringBuilder(strNJson);
                builder.Replace("@title", title);
                builder.Replace("@text", text);
                streamWriter.Write(builder);
                streamWriter.Flush();
            }

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                result = streamReader.ReadToEnd();
            }
            return result;
        }
    }
}
