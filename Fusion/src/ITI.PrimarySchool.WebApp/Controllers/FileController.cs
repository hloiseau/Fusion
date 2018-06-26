using System;
using Fusion.WebApp.Models.FileViewModels;
using System.IO;
using System.Threading.Tasks;
using Fusion.WebApp.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using System.Diagnostics;

namespace Fusion.WebApp.Controllers
{
    [Route("api/[controller]")]
    public class FileController : Controller
    {
        private readonly IHubContext<VueHub> _hubContext;
        private readonly IHostingEnvironment _appEnvironment;

        public FileController(IHostingEnvironment appEnvironment, IHubContext<VueHub> hubContext)
        {
            _appEnvironment = appEnvironment;
            _hubContext = hubContext;
        }

        [HttpGet]
        public IActionResult Upload_Image()
        {
            return View();
        }


        [HttpPost]
        public async Task<IActionResult> StockFile(IFormFile file)
        {
            if (file == null) throw new Exception("File is null");
            if (file.Length == 0) throw new Exception("File is empty");

            string path_UserName = "C:\\Users\\"+ Environment.UserName + "\\Documents\\FusionFile";
            string path_to_Images = path_UserName + "\\" + file.FileName;

            CreateFolder(path_UserName);

            using (var stream = new FileStream(path_to_Images, FileMode.Create))
            {
                await file.CopyToAsync(stream);
            }

            ViewData["FilePath"] = path_to_Images;

            string result = NotificationFactory.SendNotificationFromFirebaseCloud("File sending to number", file.FileName, "file");
            return Ok();
        }

        [HttpGet("getfile")]
        public async Task<IActionResult> SendFile()
        {
            string path_UserName = "C:\\Users\\" + Environment.UserName + "\\Documents\\FusionFile";
            string path_to_Images = path_UserName + "\\";

            var path = Path.Combine(path_to_Images);
            var stream = System.IO.File.OpenRead(path_to_Images);

            return File(stream,"image/jpg","tessssst.jpg");
            
        }

        private void CreateFolder(string path)
        {
            if(!Directory.Exists(path)) { 
                Directory.CreateDirectory(path);
                Console.WriteLine("The directory was created successfully at {0}.", Directory.GetCreationTime(path));
            }
        }

        [HttpPost("urlsend")]
        public async Task<IActionResult> sendURL([FromBody] string link)
        {
            string result = NotificationFactory.SendNotificationFromFirebaseCloud("Sending URL", link, "URL");
            return Ok();
        }

        [HttpPost("receivedurl")]
        public async Task NotifyUrRL([FromBody] string url)
        {
            await _hubContext.Clients.All.SendAsync("receivedURL", url);
        }

        [HttpPost("openurl")]
        public async Task<IActionResult> OpenURL([FromBody] string link)
        {
            Process.Start(link);
            return Ok();
        }

    }
}