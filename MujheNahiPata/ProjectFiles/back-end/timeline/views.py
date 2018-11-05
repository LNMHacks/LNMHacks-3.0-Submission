from django.http import HttpResponse
from django.views.generic import TemplateView
from django.shortcuts import redirect , render
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required

@login_required
def timeline(request):
    return render(request, 'timeline.html')



def post_new(request):
    form = PostForm()
    return render(request, 'blog/post_edit.html', {'form': form})    
