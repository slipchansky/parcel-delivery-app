from api_framework.all_apis import all_apis



def test_from_create_to_finish():
    admin, courier, client = all_apis()
    courier_id = courier.user_id
    admin_id = admin.user_id
    client_id = client.user_id

    # test register courier ####################################################
    response, body = admin.new_courier(courier_id)
    assert response.status_code == 200
    assert body['id'] == courier_id

    # admin is able to find courier in list of all couriers
    response, body = admin.get_couriers()
    assert response.status_code == 200
    assert any(lambda i: i['id'] == courier_id for i in body) is True

    # admin is able to find courier in list of free couriers
    response, body = admin.get_couriers_free()
    assert response.status_code == 200
    assert any(lambda i: i['id'] == courier_id for i in body) is True

    # test client creates order ####################################################
    response, body = client.new_order(a_from="point A", a_to="point Z", email="contact@email.com", phone="0713333333")
    assert response.status_code == 200
    assert body['client'] == client_id
    assert body['addressFrom'] == 'point A'
    assert body['addressTo'] == 'point Z'
    assert body['clientPhone'] == "0713333333"
    assert body['clientEmail'] == "contact@email.com"
    assert body['status'] == "CREATED"
    assert body['created'] is not None
    assert body['modified'] == body['created']

    #assert any(lambda i: i['id'] == courier_id for i in body) is True
    tracking_code = body['id']
    client_order = body

    # test client sees order
    response, body = client.get_order(tracking_code)
    assert response.status_code == 200
    assert body == client_order

    # test client sees order in the list of his orders
    response, body = client.get_orders()
    assert response.status_code == 200
    assert any(lambda o: o['id'] == tracking_code for o in body) is True

    # admin sees created order by id as his task ####################################################
    response, body = admin.get_task(tracking_code)
    assert response.status_code == 200
    assert body['id'] == tracking_code
    assert body['addressFrom'] == 'point A'
    assert body['addressTo'] == 'point Z'
    assert body['clientPhone'] == "0713333333"
    assert body['clientEmail'] == "contact@email.com"
    assert body['created'] is not None
    assert body['modified'] == body['created']
    assert body['status'] == "CREATED"
    assert body['state'] == "NEW"
    assert type(body['history']) is list
    assert len(body['history']) == 1
    admin_task = body


    # admin sees order by id among unassigned tasks
    response, body = admin.get_tasks_unassigned()
    assert response.status_code == 200
    o = [o for o in body if o['id'] == tracking_code]
    assert len(o) == 1
    body = o[0]
    assert body['id'] == tracking_code
    assert body['addressFrom'] == 'point A'
    assert body['addressTo'] == 'point Z'
    assert body['clientPhone'] == "0713333333"
    assert body['clientEmail'] == "contact@email.com"
    assert body['created'] is not None
    assert body['modified'] == body['created']
    assert body['status'] == "CREATED"
    assert body['state'] == "NEW"

    # test admin assignes task to courier ###################################################
    response, body = admin.assign_task(tracking_code, courier_id)
    assert response.status_code == 200
    assert body['status'] == "ASSIGNED"
    assert body['state'] == "PROGRESS"
    assert body['assignee'] is not None
    assert body['assignee']['id'] == courier_id

    # admin sees order by id among all tasks
    response, body = admin.get_tasks()
    assert response.status_code == 200
    o = [o for o in body if o['id'] == tracking_code]
    assert len(o) == 1

    # courier sees assigned task among the tasks that assigned to he
    response, body = courier.get_tasks()
    assert response.status_code == 200
    o = [o for o in body if o['id'] == tracking_code]
    assert len(o) == 1
    courier_task = o[0]
    assert courier_task['id'] == tracking_code
    assert courier_task['addressFrom'] == 'point A'
    assert courier_task['addressTo'] == 'point Z'
    assert courier_task['clientPhone'] == "0713333333"
    assert courier_task['clientEmail'] == "contact@email.com"

    #client sees order status modification
    response, body = client.get_order(tracking_code)
    assert response.status_code == 200
    assert body['status'] == 'ASSIGNED'


    # test courier starts delivering ###################################################
    response, body = courier.start_task(id=tracking_code)
    assert response.status_code == 200
    assert body['status'] == 'INPROGRESS'

    response, body = courier.get_task(tracking_code)
    assert response.status_code == 200
    assert body['status'] == 'INPROGRESS'


    response, body = client.get_order(tracking_code)
    assert response.status_code == 200
    assert body['status'] == 'INPROGRESS'

    response, body = admin.get_task(tracking_code)
    assert response.status_code == 200
    assert body['status'] == 'INPROGRESS'
    assert type(body['history']) is list
    assert len(body['history']) == 3


    # test courier updates location ###################################################
    response, body = courier.set_location(tracking_code, 'point B')
    assert response.status_code == 200
    # TODO

    response, body = courier.get_task(tracking_code)
    assert response.status_code == 200
    assert body['location'] == 'point B'


    response, body = client.get_order(tracking_code)
    assert response.status_code == 200
    assert body['location'] == 'point B'

    response, body = admin.get_task(tracking_code)
    assert response.status_code == 200
    assert body['location'] == 'point B'
    assert type(body['history']) is list
    assert len(body['history']) == 4

    # test courier finalizes delivery ###################################################
    response, body = courier.task_complete(tracking_code)
    assert response.status_code == 200

    response, body = courier.get_tasks()
    assert response.status_code == 200
    o = [o for o in body if o['id'] == tracking_code]
    assert len(o) == 0

    response, body = client.get_order(tracking_code)
    assert response.status_code == 200
    assert body['status'] == 'FINISHED'

    response, body = admin.get_task(tracking_code)
    assert response.status_code == 200
    assert body['status'] == 'FINISHED'
    assert body['state'] == 'FINISHED'
    assert type(body['history']) is list
    assert len(body['history']) == 5

