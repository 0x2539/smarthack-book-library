from django.contrib import admin
from .models import Book, Loan

admin.site.register(Book)
admin.site.register(Loan)

admin.site.site_header = "read admin"
admin.site.index_title = "Librarian's favorite page"
