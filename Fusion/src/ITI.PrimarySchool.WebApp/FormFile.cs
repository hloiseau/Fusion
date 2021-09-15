using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace Fusion.WebApp
{
    public class FormFile : IFormFile
    {
        string IFormFile.ContentType => throw new NotImplementedException();

        string IFormFile.ContentDisposition => throw new NotImplementedException();

        IHeaderDictionary IFormFile.Headers => throw new NotImplementedException();

        long IFormFile.Length => throw new NotImplementedException();

        string IFormFile.Name => throw new NotImplementedException();

        string IFormFile.FileName => throw new NotImplementedException();

        void IFormFile.CopyTo(Stream target)
        {
            throw new NotImplementedException();
        }

        Task IFormFile.CopyToAsync(Stream target, CancellationToken cancellationToken)
        {
            throw new NotImplementedException();
        }

        Stream IFormFile.OpenReadStream()
        {
            throw new NotImplementedException();
        }
    }
}
