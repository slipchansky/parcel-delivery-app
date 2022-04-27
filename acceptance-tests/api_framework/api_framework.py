from api_framework.auth_framework import AuthAPIFramework
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
        self.user_id = user
        self.api_prefix = prefix
        auth_api = AuthAPIFramework(base_url)
        self.authenticated, token = auth_api.create_token(user, password)

        if self.authenticated:
            self.headers = {'Authorization' : token}


    def is_authenticated(self):
        return self.authenticated

    def call_qualified_resource(self, method,  url, qualifier='', data=None, **kvargs):
        qparams = ""
        sep = ''
        for kvarg, replacement in kvargs.items():
            x = '{' + kvarg + '}'
            if x in url:
                url = url.replace(x, replacement)
            else:
                qparams=qparams+sep+kvarg+'='+replacement
                sep = ','

        if qparams != '':
            url = url + '?' + qparams
        try:
            func = getattr(requests, method)
            url = self.base_url + base_prefix + qualifier + url
            response_json = None
            if data is not None:
                response_json = func(url, json=data, headers=self.headers)
            else:
                response_json = func(url, json=data, headers=self.headers)
        except AttributeError:
            print(f"Error calling API by {method} {url}")
            raise Exception("Error Error calling API")
        return response_json, json.loads(response_json.content)

    def call(self, method, url, data=None, **kvargs):
        return self.call_qualified_resource(method, url, self.api_prefix, data, **kvargs)


    def get(self, url, data=None, **kvargs):
        return self.call('get', url, data, **kvargs)

    def post(self, url, data=None, **kvargs):
        return self.call('post', url, data, **kvargs)

    def put(self, url, data=None, **kvargs):
        return self.call('put', url, data, **kvargs)

    def head(self, url, data=None, **kvargs):
        return self.call('head', url, data, **kvargs)

    def delete(self, url, data=None, **kvargs):
        return self.call('delete', url, data, **kvargs)

