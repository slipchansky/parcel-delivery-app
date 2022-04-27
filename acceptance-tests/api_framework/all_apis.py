from uuid import uuid4 as randomuuid
from api_framework.admin_api import AdminApi
from api_framework.courier_api import CourierApi
from api_framework.client_api import ClientApi
from api_framework.auth_api import  AuthApi
from api_framework.env import base_url

base_url = base_url()

def random_str():
    return str(randomuuid()).replace('-', '')


def make_api(api_class, username=None, password=None, roles=["ROLE_CLIENT"]):
    if username is None:
        username = random_str()
    if password is None:
        password = random_str()

    auth = AuthApi(base_url, 'super', 'password')
    ok = False
    if auth.is_authenticated():
        ok = auth.with_user(username=username, password=password, roles=roles)
    else:
        raise Exception("Coul't authorize user 'super'")
    if not ok:
        raise Exception("Couldn't get user for testing")

    api = api_class(base_url=base_url, username=username, password=password)
    if not api.is_authenticated():
        raise Exception("Couldn't authenticate admin")
    return api

def all_apis():
    #["ROLE_CLIENT", "ROLE_ADMIN", "ROLE_COURIER"]
    admin_api = make_api(AdminApi,  roles=["ROLE_ADMIN"])
    courier_api = make_api(CourierApi, roles=["ROLE_COURIER"])
    client_api = make_api(ClientApi, roles=["ROLE_CLIENT"])

    # courier = CourierApi(base_url=base_url, username=username, password=password)
    # if not courier.is_authenticated():
    #     raise Exception("Couldn't authenticate courier")
    #
    # client = ClientApi(base_url=base_url, username=username, password=password)
    # if not client.is_authenticated():
    #     raise Exception("Couldn't authenticate client")

    return admin_api, courier_api, client_api

