# Getting Started
Sample spring3 graphql application netflix-dgs support and mongodb database

# Graphql Request
http://localhost:8080/movie/graphql

<img title="a title" alt="PostMan Request" src="/image/graphql_req.png">


## Query


```graphql
query getmovies($movieInput1: MovieInput) {
    movies(searchMovie: $movieInput1){
        id
        plot
        title
        year
    }
}
```
## Variables

Variables
```json
{
 "movieInput1": {
   "yearStart" : "1903",
   "yearEnd" : "1920"
   }
}
```

## Response

Response
```json
{
    "data": {
        "movies": [
            {
                "id": "573a1390f29313caabcd446f",
                "plot": "A greedy tycoon decides, on a whim, to corner the world market in wheat. This doubles the price of bread, forcing the grain's producers into charity lines and further into poverty. The film...",
                "title": "A Corner in Wheat",
                "year": "1909"
            },
            {
                "id": "573a1390f29313caabcd4803",
                "plot": "Cartoon figures announce, via comic strip balloons, that they will move - and move they do, in a wildly exaggerated style.",
                "title": "Winsor McCay, the Famous Cartoonist of the N.Y. Herald and His Moving Comics",
                "year": "1911"
            },
            {
                "id": "573a13a5f29313caabd13572",
                "plot": "Cartoon figures announce, via comic strip balloons, that they will move - and move they do, in a wildly exaggerated style.",
                "title": "Winsor McCay, the Famous Cartoonist of the N.Y. Herald and His Moving Comics",
                "year": "1911"
            },
            {
                "id": "573a1390f29313caabcd4323",
                "plot": "A young boy, opressed by his mother, goes on an outing in the country with a social welfare group where he dares to dream of a land where the cares of his ordinary life fade.",
                "title": "The Land Beyond the Sunset",
                "year": "1912"
            },
            {
                "id": "573a13a5f29313caabd14875",
                "plot": "A young boy, opressed by his mother, goes on an outing in the country with a social welfare group where he dares to dream of a land where the cares of his ordinary life fade.",
                "title": "The Land Beyond the Sunset",
                "year": "1912"
            },
            {
                "id": "573a1390f29313caabcd4eaf",
                "plot": "A woman, with the aid of her police officer sweetheart, endeavors to uncover the prostitution ring that has kidnapped her sister, and the philanthropist who secretly runs it.",
                "title": "Traffic in Souls",
                "year": "1913"
            },
            {
                "id": "573a1390f29313caabcd50e5",
                "plot": "The cartoonist, Winsor McCay, brings the Dinosaurus back to life in the figure of his latest creation, Gertie the Dinosaur.",
                "title": "Gertie the Dinosaur",
                "year": "1914"
            },
            {
                "id": "573a1390f29313caabcd516c",
                "plot": "Original advertising for the film describes it as a drama of primitive life on the shores of the North Pacific...",
                "title": "In the Land of the Head Hunters",
                "year": "1914"
            },
            {
                "id": "573a1390f29313caabcd5293",
                "plot": "Young Pauline is left a lot of money when her wealthy uncle dies. However, her uncle's secretary has been named as her guardian until she marries, at which time she will officially take ...",
                "title": "The Perils of Pauline",
                "year": "1914"
            },
            {
                "id": "573a1390f29313caabcd548c",
                "plot": "The Civil War divides friends and destroys families, but that's nothing compared to the anarchy in the black-ruled South after the war.",
                "title": "The Birth of a Nation",
                "year": "1915"
            },
            {
                "id": "573a1390f29313caabcd5501",
                "plot": "A venal, spoiled stockbroker's wife impulsively embezzles $10,000 from the charity she chairs and desperately turns to a Burmese ivory trader to replace the stolen money.",
                "title": "The Cheat",
                "year": "1915"
            },
            {
                "id": "573a1390f29313caabcd56df",
                "plot": "An immigrant leaves his sweetheart in Italy to find a better life across the sea in the grimy slums of New York. They are eventually reunited and marry. But life in New York is hard and ...",
                "title": "The Italian",
                "year": "1915"
            },
            {
                "id": "573a1390f29313caabcd587d",
                "plot": "At 10 years old, Owens becomes a ragged orphan when his sainted mother dies. The Conways, who are next door neighbors, take Owen in, but the constant drinking by Jim soon puts Owen on the ...",
                "title": "Regeneration",
                "year": "1915"
            },
            {
                "id": "573a1390f29313caabcd5967",
                "plot": "An intrepid reporter and his loyal friend battle a bizarre secret society of criminals known as The Vampires.",
                "title": "Les vampires",
                "year": "1915"
            },
            {
                "id": "573a1390f29313caabcd5a93",
                "plot": "Christ takes on the form of a pacifist count to end a senseless war.",
                "title": "Civilization",
                "year": "1916"
            },
            {
                "id": "573a1390f29313caabcd5b9a",
                "plot": "In the wayward western town known as Hell's Hinges, a local tough guy is reformed by the faith of a good woman.",
                "title": "Hell's Hinges",
                "year": "1916"
            },
            {
                "id": "573a1390f29313caabcd5c0f",
                "plot": "The story of a poor young woman, separated by prejudice from her husband and baby, is interwoven with tales of intolerance from throughout history.",
                "title": "Intolerance: Love's Struggle Throughout the Ages",
                "year": "1916"
            },
            {
                "id": "573a1390f29313caabcd5ea4",
                "plot": "A District Attorney's outspoken stand on abortion gets him in trouble with the local community.",
                "title": "Where Are My Children?",
                "year": "1916"
            },
            {
                "id": "573a1390f29313caabcd60e4",
                "plot": "Charlie is an immigrant who endures a challenging voyage and gets into trouble as soon as he arrives in America.",
                "title": "The Immigrant",
                "year": "1917"
            },
            {
                "id": "573a1390f29313caabcd6223",
                "plot": "Gwen's family is rich, but her parents ignore her and most of the servants push her around, so she is lonely and unhappy. Her father is concerned only with making money, and her mother ...",
                "title": "The Poor Little Rich Girl",
                "year": "1917"
            },
            {
                "id": "573a1390f29313caabcd6377",
                "plot": "A rich young Easterner who has always wanted to live in \"the Wild West\" plans to move to a Western town. Unknown to him, the town's \"wild\" days are long gone, and it is an orderly, ...",
                "title": "Wild and Woolly",
                "year": "1917"
            },
            {
                "id": "573a1390f29313caabcd63d6",
                "plot": "Two peasant children, Mytyl and Tyltyl, are led by Berylune, a fairy, to search for the Blue Bird of Happiness. Berylune gives Tyltyl a cap with a diamond setting, and when Tyltyl turns the...",
                "title": "The Blue Bird",
                "year": "1918"
            },
            {
                "id": "573a1390f29313caabcd680a",
                "plot": "A frail waif, abused by her brutal boxer father in London's seedy Limehouse District, is befriended by a sensitive Chinese immigrant with tragic consequences.",
                "title": "Broken Blossoms or The Yellow Man and the Girl",
                "year": "1919"
            },
            {
                "id": "573a1391f29313caabcd68d0",
                "plot": "A penniless young man tries to save an heiress from kidnappers and help her secure her inheritance.",
                "title": "From Hand to Mouth",
                "year": "1919"
            }
        ]
    }
}
```



# Micrometer-tracing metrics
http://localhost:8080/actuator/metrics/movies.get 
<br />
http://localhost:8080/actuator/metrics/disk.total

