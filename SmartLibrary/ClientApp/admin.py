from django.contrib import admin
from .models import Book, Loan


class LoanAdmin(admin.ModelAdmin):
    list_display = ['user', 'book', 'start_date', 'return_date']


class BookAdmin(admin.ModelAdmin):
    list_display = ['title', 'short_name']


admin.site.register(Book, BookAdmin)
admin.site.register(Loan, LoanAdmin)

admin.site.site_header = "read"
admin.site.index_title = "Librarian's favorite page"
