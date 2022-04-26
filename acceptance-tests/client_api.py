from api_framework import BaseApiFramework


api_prefix = "/client"

deliveries = "/deliveries"
deliveries_one = deliveries+"/{order_id}"

class ClientApi(BaseApiFramework):
    def __init__(self, base_url, username, password):
        BaseApiFramework.__init__(self, base_url, api_prefix, username, password)

    def new_order(self, a_from, a_to, email, phone):
        return self.post(deliveries, data={
                              "addressFrom": a_from,
                              "addressTo": a_to,
                              "clientEmail": email,
                              "clientPhone": phone
                            })

    def get_orders(self):
        return self.get(deliveries)

    def get_order(self, order_id):
        return self.get(deliveries_one, order_id=order_id)

    def update_address(self, order_id, a_to):
        return self.put(deliveries_one, order_id=order_id,
                        data={
                           "addressTo": a_to
                        })

    def cancel_order(self, order_id):
        return self.delete(deliveries_one, order_id=order_id)
