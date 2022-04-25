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


a = AuthApi("http://localhost:8080", 'super', 'password')
if a.is_authenticated():
    r, body = a.sign_up(username='test1', password='sql', email='testuser1@test.com', roles=["ROLE_CLIENT", "ROLE_ADMIN", "ROLE_COURIER"])
    k = 0
m = 1