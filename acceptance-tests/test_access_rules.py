from api_framework.all_apis import all_apis
import requests
from api_framework.env import base_url

base_url = base_url()

def test_unauthorized():
    response = requests.get(base_url+'/ap/v1/admin/couriers')
    assert response.status_code == 401
    response = requests.get(base_url+'/ap/v1/client/deliveries')
    assert response.status_code == 401
    response = requests.get(base_url+'/ap/v1/courier/tasks')
    assert response.status_code == 401


def test_access_denied():
    admin, courier, client = all_apis()
    response, body = admin.call_qualified_resource ('get', '/deliveries', '/client')
    assert response.status_code == 403
    response, body = admin.call_qualified_resource ('get', '/tasks', '/courier')
    assert response.status_code == 403

    response, body = courier.call_qualified_resource('get', '/deliveries', '/client')
    assert response.status_code == 403
    response, body = courier.call_qualified_resource('get', '/tasks', '/admin')
    assert response.status_code == 403

    response, body = client.call_qualified_resource ('get', '/tasks', '/courier')
    assert response.status_code == 403
    response, body = client.call_qualified_resource('get', '/tasks', '/admin')
    assert response.status_code == 403

