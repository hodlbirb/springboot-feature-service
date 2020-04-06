# moneylion-feature-service
Feature configuration service

## Setup

Pull the image from Docker, bootstrap the container and start the RPC service:

```
docker run -it -p9000:8080 \
      -v ${PWD}:/opt/service \
      --name feature_service \
      nrdwnd/moneylion-feature-service \
      make run
```

## GET check current user's access to the feature

Request:

```curl -s "[::]:9000/feature?email=hello@world&featureName=canWithdraw" | json_xs```

Response:

```
{
   "canAccess" : true
}

```

## POST alter user's access to the feature

Request:

```
curl -v -H'Content-Type: application/json' \
  --data \
    '{
      "email":"hello@world",
      "featureName":"canWithdraw",
      "enable": false
    }' "[::]:9000/feature"
```

Response (Not modified, HTTP Status 304):

```
> POST /feature HTTP/1.1
> Host: [::]:9000
> User-Agent: curl/7.64.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 92

< HTTP/1.1 304
< Date: Mon, 06 Apr 2020 10:38:52 GMT
```

Response (Modified, HTTP Status 200):

```
> POST /feature HTTP/1.1
> Host: [::]:9000
> User-Agent: curl/7.64.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 93

< HTTP/1.1 200
< Content-Length: 0
< Date: Mon, 06 Apr 2020 10:41:52 GMT
```
