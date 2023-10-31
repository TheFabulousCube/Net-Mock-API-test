using Microsoft.AspNetCore.Routing.Constraints;

namespace Net_Mock_API_test
{
    public class CatBreeds
    {
    public string? breed { get; set; }
        public string? country { get; set; }
    public string? origin { get; set; }
    public string? coat { get; set; }
    public string? pattern { get; set; }
    }

    public class CatFacts
    { public string? fact { get; set; }
        public int length { get; set; } 
    }
}
