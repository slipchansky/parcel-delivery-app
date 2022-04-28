start cmd /c "java -Dspring.profiles.active=dev -jar .\parceldelivery-eureka\target\parceldelivery-eureka-0.0.1-SNAPSHOT.jar                  "
start cmd /c "java -Dspring.profiles.active=dev -jar .\parcel-delivery-publicapi\target\parcel-delivery-publicapi-0.0.1-SNAPSHOT.jar          "
start cmd /c "java -Dspring.profiles.active=dev -jar .\parceldelivery-admin-service\target\parceldelivery-admin-service-0.0.1-SNAPSHOT.jar    "
start cmd /c "java -Dspring.profiles.active=dev -jar .\parceldelivery-client-service\target\parceldelivery-client-service-0.0.1-SNAPSHOT.jar  "
start cmd /c "java -Dspring.profiles.active=dev -jar .\parceldelivery-courier-service\target\parceldelivery-courier-service-0.0.1-SNAPSHOT.jar"
start cmd /c "java -Dspring.profiles.active=dev -jar .\parceldelivery-user-service\target\parceldelivery-user-service-0.0.1-SNAPSHOT.jar      "

#> logs/eureka.log 2<&1 
#> logs/pubapi.log 2<&1 
#> logs/admin.log 2<&1  
#> logs/client.log 2<&1 
#> logs/courier.log 2<&1
#> logs/user.log 2<&1   