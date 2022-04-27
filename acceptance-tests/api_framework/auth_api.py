from api_framework.api_framework import BaseApiFramework


api_prefix = "/auth"
class AuthApi(BaseApiFramework):
    def __init__(self, base_url, username, password):
        BaseApiFramework.__init__(self, base_url, api_prefix, username, password)

    def sign_up(self, username, password, email, roles=None):
        if roles is None:
            roles = ["ROLE_CLIENT"]
        return self.call('post', '/signup', data={
                                      "username": username,
                                      "email": email,
                                      "password": password,
                                      "roles": roles
                                    })

    def with_user(self, username, password, email=None, roles=["ROLE_CLIENT", "ROLE_ADMIN", "ROLE_COURIER"]):
        if self.is_authenticated():
            if email is None:
                email = username+'@'+username+'.com'
            r, body = self.sign_up(username=username, password=password, email=email, roles=roles)
            if r.status_code == 500:
                if type(body['message']) is list:
                    if body['message'][0] == 'User already exists':
                        return True

            elif r.status_code == 200:
                return True
        return False


