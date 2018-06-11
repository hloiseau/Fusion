using System;
using System.IO;
using System.Threading.Tasks;
using Fusion.WebApp.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace Fusion.WebApp.Controllers
{
    [Route("api/[controller]")]
    public class FileController : Controller
    {
        private readonly IHostingEnvironment _appEnvironment;
        private IFormFile _file { get; set; }

        public FileController(IHostingEnvironment appEnvironment)
        {
            _appEnvironment = appEnvironment;
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

            _file = file;

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
        public async Task<IActionResult> SendFile([FromBody] String fileName)
        {
            string path_UserName = "C:\\Users\\" + Environment.UserName + "\\Documents\\FusionFile";
            string path_to_Images = path_UserName + "\\" + fileName;

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



    }
}