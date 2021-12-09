# Feature configuration service

## Environment setup

### Docker

Pull a prebuilt image from Docker Hub, bootstrap the container and start the RPC service in detached mode:

```
docker run -it -d -p9000:8080 \
      -v ${PWD}:/opt/service \
      --name feature_service \
      nrdwnd/moneylion-feature-service \
      make run
```

* Note: The command above should be executed from the folder containing this project

### Local environment

Or run locally:

```
make run
```

## Health check

After setup check service health status:

```curl -s "http://[::]:9000/actuator/health" | json_pp ```

It should respond with:

```
{
   "status" : "UP"
}
```

## GET /feature?email=&featureName=

Use this endpoint to check user's feature access

### Request

```curl -s "[::]:9000/feature?email=hello@world&featureName=canWithdraw" | json_pp```

### Response

```
{
   "canAccess" : true
}

```

## POST /feature

Use this endpoint to alter user's feature access

### Request

```
curl -v -H'Content-Type: application/json' \
  --data \
    '{
      "email":"hello@world",
      "featureName":"canWithdraw",
      "enable": false
    }' "[::]:9000/feature"
```

### Response (Not modified, HTTP Status 304)

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

### Response (Modified, HTTP Status 200)

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
