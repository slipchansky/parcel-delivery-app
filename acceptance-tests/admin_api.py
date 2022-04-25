from api_framework import BaseApiFramework

api_prefix = "/auth"
class AuthApi(BaseApiFramework):
    def __init__(self, base_url, user, password):
        BaseApiFramework.__init__(self, base_url, api_prefix, user, password)

    def sign_up(self, username, password, email, roles=None):
        if roles is None:
            roles = ["ROLE_CLIENT"]
        return self.call('post', '/signup', data={
                                      "username": username,
                                      "email": email,
                                      "password": password,
                                      "roles": roles
                                    })







