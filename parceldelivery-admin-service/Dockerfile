#
# Copyright (c) 2019 Red Hat, Inc.
# This program and the accompanying materials are made
# available under the terms of the Eclipse Public License 2.0
# which is available at:
#
#     https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#
# Contributors:
#   Red Hat, Inc. - initial API and implementation
#

ARG VERSION=latest
FROM openjdk:8u322-jdk
COPY target/parceldelivery-admin-service-0.0.1-SNAPSHOT.jar  /deployments/parceldelivery-admin-service-0.0.1-SNAPSHOT.jar

EXPOSE 8002
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "/deployments/parceldelivery-admin-service-0.0.1-SNAPSHOT.jar"]
