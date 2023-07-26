import pytest
from scheduleAPI import API

def test_init(product_name="Knz benetto"):
    api = API(product_name)
    assert api.product_name == product_name

def test_init2(product_name="Sara"):
    api = API(product_name)
    assert api.product_name == product_name

def test_getProductId(product_name="Knz benetto"):
    api = API(product_name)
    assert api.getProductId(product_name) == 1

def test_getProductId2(product_name="Sara"):
    api = API(product_name)
    assert api.getProductId(product_name) == 10

def test_getNeededMaterials(product_name="Knz benetto"):
    api = API(product_name)
    id = api.getProductId(product_name)
    assert api.getNeededProfession(id) == [('krawiec', 5), ('stolarz', 6), ('pomocnik stolarza', 3), ('tapicer', 4), ('kontroler jakosci', 1), ('kierowca', 3)]

# def test_getProductId2(product_name="Sara"):
#     api = API(product_name)
#     id = api.getProductId(product_name)
#     print(api.getNeededProfession(id))
#     #assert api.getNeededProfession(id) == ???

def test_getHoursForEmployees(product_name="Knz benetto"):
    api = API(product_name)
    api.getHoursForEmployees()
    assert [api.product_name, api.product_id, api.professions_needed_time] == [product_name, 1, [('krawiec', 5), ('stolarz', 6), ('pomocnik stolarza', 3), ('tapicer', 4), ('kontroler jakosci', 1), ('kierowca', 3)]]

# def test_getHoursForEmployees(product_name="Sara"):
#     api = API(product_name)
#     api.getHoursForEmployees()
#     assert [api.product_name, api.product_id, api.professions_needed_time] == [product_name, 10, ???]
