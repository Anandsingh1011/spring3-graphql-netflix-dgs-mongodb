# Getting Started
Sample spring3 graphql application netflix-dgs support and mongodb database

# Graphql Request
http://localhost:8080/movie/graphql

## Query
query getmovies($movieInput1: MovieInput) {
    movies(searchMovie: $movieInput1){
        id
        plot
        title
        year
    }
}

## Variables

{
 "movieInput1": {
   "yearStart" : "1903",
   "yearEnd" : "1920"
   }
}



# Micrometer-tracing metrics
http://localhost:8080/actuator/metrics/movies.get
http://localhost:8080/actuator/metrics/disk.total

