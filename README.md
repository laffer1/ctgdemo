# ctgdemo

This repository contains demo applications illustrating how to use Spring Webflux. 

Spring Webflux is a reactive framework in the Spring ecosystem to utilize reactive patterns
with project reactor. 

For those familiar with typical Spring boot applications, there are a few differences. 

The API for using spring security changed in webflux applications. Old configurations will not work,
 and older spring boot versions lacked support for features such as protecting an app with oauth and
also proving unsecured endpoints. Spring boot 2.1.x is also missing several fields for logging with
the netty backend. 

You can use tomcat, jetty, and netty with reactive, but the real performance benefit comes with
using netty.  The tradeoff is that you lose legacy servlet API features such as filters, sessions, 
and third party libraries that can run on to of the servlet API.  It's possible to upgrade
a legacy application on tomcat to use reactive patterns and even switch to reactive controllers. 

Migration should be done from the repository, rest template, or other backing up through the service
layer and the controllers should be done last. 

Not all spring data integrations support reactive patterns. You can use cassandra, mongodb, 
redis, kafka and a few others with reactive patterns. JDBC sources with relational databases
are not reactive, although some newer drivers are starting to support features needed to add
reactive libraries. (oracle for example) 

## Demo 1 - thymeleaf-standalone

This examples makes use of Thymeleaf templates to create a server side reactive web application
with a view that displays categories. There is an intentional delay added of 1 second to simulate
loading a list from a remote source into the template. 

The Service class CategoryService, simply returns a Flux backed by a list of categories. In 
project reactor, a Flux is a reactive type for multiple objects. A Mono is used for individual
items. It's possible to return a mono of a list, but it's not as useful when trying to render
as we stream data.

In order to provide data to the model in a reactive way, we had to handle the data source
special in the HomeController by wrapping it in a ReactiveDataDriverContextVariable. 

   IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(categoryService.getCategories(), 1);

While this app uses reactive features, it is not using netty as it does not play nice with
server side templating.

## Demo 2 - thymeleaf-webclient

Based on Demo 1, this example shows how to use the reactive WebClient to fetch data
from a third party REST API.  

The CategoryService now uses the webclient created in WebClientConfig to access the API. 
We set the base url and custom timeout settings for the connection.  

## References

* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Data Reactive Redis](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-redis)
