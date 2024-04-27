>>HEAD
# Book Shop -service

### Automation of Book Shop  operation systems

This system includes:

* Reformed a User details
* Chance to perform actions on Book
* Chance to perform actions on card
* Using Spring security to authenticate users
* the ability to save orders to the Cart
* granting and restricting permissions and roles to Users by super admin
* Get user statistics of all orders books
* View all orders

```java

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableWebSecurity
public class BookShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }
 /* SecurityContextHolder.getContext().getAuthentication().getName();
            principal.getName();*/
}
```

***You can get the services listed above in this table through the links***

| No |                                                                               Services                                                                               | Status |
|:--:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:------:|
| 1  |            [User Service](https://github.com/QodirovHudoberdi/bookShopNew/blob/master/src/main/java/com/company/bookShop/controller/UserController.java)             |   ✅    |
| 2  |           [Admin Service](https://github.com/QodirovHudoberdi/bookShopNew/blob/master/src/main/java/com/company/bookShop/controller/AdminController.java)            |   ✅    |
| 3  |            [Book Service](https://github.com/QodirovHudoberdi/bookShopNew/blob/master/src/main/java/com/company/bookShop/controller/BookController.java)             |   ✅    |
| 4  |         [Comment Service](https://github.com/QodirovHudoberdi/bookShopNew/blob/master/src/main/java/com/company/bookShop/controller/CommentController.java)          |   ✅    |
| 5  |          [Basket Service ](https://github.com/QodirovHudoberdi/bookShopNew/blob/master/src/main/java/com/company/bookShop/controller/BasketController.java)          |   ✅    |
| 6  | [Order Details Service](https://github.com/QodirovHudoberdi/bookShopNew/blob/master/src/main/java/com/company/bookShop/controller/OrderDetailController.java) |   ✅    |
| 7  |        [Order Service](https://github.com/QodirovHudoberdi/bookShopNew/blob/master/src/main/java/com/company/bookShop/controller/OrderController.java)         |   ✅    |
| 8  |   [Statistics Service](https://github.com/QodirovHudoberdi/bookShopNew/blob/master/src/main/java/com/company/bookShop/controller/StatsController.java)    |   ✅    |

>>>>origin/master