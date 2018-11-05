from django.urls import path

from .views import logout,signup
from  .import views as core_views
from django.contrib.auth import views as auth_views

urlpatterns = [
    path('login/', auth_views.login,{'template_name': 'login.html'}, name='login'),
    path('logout/', auth_views.logout,{'next_page': '../login'},name='logout'),
    path('register/', core_views.signup, name='signup'),

]
