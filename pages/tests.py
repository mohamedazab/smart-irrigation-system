from django.test import TestCase

# Create your tests here.
class YourTestClass(TestCase):
    @classmethod
    def setUpTestData(cls):
        print("setUpTestData: Run once to set up non-modified data for all class methods.")
        pass

    def setUp(self):
        print("setUp: Run once for every test method to setup clean data.")
        pass
        
    def test_one_plus_one_equals_two(self):
            print("Method: test_one_plus_one_equals_two (dummy test).")
            self.assertEqual(1 + 1, 2)
    #use this test to ensure that docker image only gets published when all tests pass
    # def test_one_plus_one_false(self):
    #         print("Method: test_one_plus_one_equals_two (dummy test).")
    #         self.assertEqual(1, 2)
