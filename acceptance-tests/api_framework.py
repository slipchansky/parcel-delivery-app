from auth_framework import AuthAPIFramework
import requests
import json

base_prefix = '/api/v1'


class BaseApiFramework(object):
    headers: {}
    base_url: ""
    api_prefix = ""
    authenticated: False
    def __init__(self, base_url, prefix, user, password):
        self.base_url = base_url
        self.api_prefix = prefix
        auth_api = AuthAPIFramework(base_url)
        self.authenticated, token = auth_api.create_token(user, password)

        if self.authenticated:
            self.headers = {'Authorization' : token}


    def is_authenticated(self):
        return self.authenticated

    def call(self, method, url, data=None, **kvargs):
        for kvarg, replacement in kvargs.items():
            url = url.replace('{' + kvarg + '}', replacement)
        try:
            func = getattr(requests, method)
            url = self.base_url + base_prefix + self.api_prefix + url
            response_json = None
            if data is not None:
                response_json = func(url, json=data, headers=self.headers)
            else:
                response_json = func(url, json=data, headers=self.headers)
        except AttributeError:
            print(f"Error calling API by {method} {url}")
            raise Exception("Error Error calling API")
        return response_json, json.loads(response_json.content)



