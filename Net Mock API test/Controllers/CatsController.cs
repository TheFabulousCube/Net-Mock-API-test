using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;

namespace Net_Mock_API_test.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class CatsController : Controller
    {
        private readonly IConfiguration _config;
        public CatsController(IConfiguration config)
        {
            _config = config;
        }

        [HttpGet("GetCatFact")]
        public async Task<string> GetAsync()
        {
            string baseurl = _config["CatsUrl"];
            string fact = "SUT - ";
            using (var httpClient = new HttpClient())
            {
                using (  var response = await httpClient.GetAsync(baseurl + "/fact"))
                {
                    string apiResponse = await response.Content.ReadAsStringAsync();
                    var catFact = JsonConvert.DeserializeObject<CatFacts>(apiResponse);
                    fact += catFact.fact;
                }

            }
            return fact;
        }
    }
}
