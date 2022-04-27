@echo off

cd .\parceldelivery-user-service\
start docker-build.cmd
cd ..

cd .\parceldelivery-client-service
start docker-build.cmd
cd ..

cd .\parceldelivery-courier-service
start docker-build.cmd
cd ..

cd .\parceldelivery-admin-service
start docker-build.cmd
cd ..

cd .\parcel-delivery-publicapi
start docker-build.cmd
cd ..

cd .\parceldelivery-eureka
start docker-build.cmd
cd ..

