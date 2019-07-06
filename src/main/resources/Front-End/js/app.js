var LINK = 'http://localhost:8080/';
var bookToUpdateId;
var TITLE_AND_PUBLISHER_REGEX = /[-,\\.a-zA-Z\d]+(( ){1}[-,\\.a-zA-Z\d]+)*/;
var AUTHOR_REGEX = /([a-zA-Z]+){1}((( ){1}[a-zA-Z,]+))*/;
var ISBN_REGEX = /^(\d){13}$/;


function AJAX(actionType, data) {
    switch (actionType) {
        case 'GET':
            if (data == -1) {
                return $.ajax(LINK + 'books');
            } else if (data >= 0) {
                return $.ajax(LINK + 'books/' + data)
            } else {
                console.log('Wrong data for GET action in function AJAX!');
                break;
            }
        case 'POST':
            return $.ajax({
                url: LINK + 'books',
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json"
            });
        case 'PUT':
            return $.ajax({
                url: LINK + 'books/' + bookToUpdateId,
                data: JSON.stringify(data),
                contentType: "application/json",
                method: "PUT"
            });
        case 'DELETE':
            return $.ajax({
                url: LINK + 'books/' + data,
                type: 'DELETE'
            })

    }
}

function getBooks() {
    AJAX('GET', -1).done(function (result) {
        result.forEach(function (book) {
            var newDiv = $('<div class="book">' +
                '<h3 data-id="' + book.id + '" class="book-title">' + book.title + '</h3>' +
                '<div class="book-details">' +
                '</div>' +
                '</div>');

            $('#books').append(newDiv);
        })

    })
}

$('body').on('click', '#display', function () {
    var displayButton = $('#display');
    if (displayButton.text() == 'Show books') {
        displayButton.text('Hide books');
        $('#books').fadeIn();
    } else {
        displayButton.text('Show books');
        $('#books').fadeOut();
        $('.book-details').fadeOut().html("").toggleClass('visible');
    }

});

$('body').on('click', 'h3.book-title', function (event) {
    var bookTitle = $(event.target);

    AJAX('GET', bookTitle.data('id')).done(function (book) {
        var bookDetails = bookTitle.parent().find('.book-details');

        if (bookDetails.hasClass('visible')) {
            bookDetails.fadeOut().html("").toggleClass('visible');
        } else {
            var newDivContent = $(
                '<p>ISBN: ' + book.isbn + '</p>' +
                '<p>Author: ' + book.author + '</p>' +
                '<p>Publisher: ' + book.publisher + '</p>' +
                '<p>Type: ' + book.type + '</p>' +
                '<button class="edit" data-id="' + book.id + '">edit</button>' +
                '<button class="delete" data-id="' + book.id + '">delete</button>');

            bookDetails.append(newDivContent).fadeIn().toggleClass('visible');
        }

    })
});

$('body').on('click', '#add', function () {
    var addBookForm = $('#addBook');
    var editBookForm = $('#editBook');
    if ($(editBookForm).css('display') == 'block') {
        editBookForm.fadeOut(800);
        setTimeout(function () {
            addBookForm.fadeIn(800);
        }, 800);
    } else {
        addBookForm.fadeIn(800);
    }

});

function fillEditBookForm(form, event) {
    bookToUpdateId = event.target.dataset.id;

    AJAX('GET', bookToUpdateId).done(function (book) {
        form.find('.title').val(book.title);
        form.find('.author').val(book.author);
        form.find('.publisher').val(book.publisher);
        form.find('.isbn').val(book.isbn);
        form.find('.type').val(book.type);
    });

    form.fadeIn(800);
}

$('body').on('click', '.edit', function (event) {
    var addBookForm = $('#addBook');
    var editBookForm = $('#editBook');
    if (addBookForm.css('display') == 'block') {
        addBookForm.fadeOut(800);
        setTimeout(function () {
            fillEditBookForm(editBookForm, event)
        }, 800);
    } else {
        fillEditBookForm(editBookForm, event)
    }
});

$('body').on('click', '.exit', function (event) {
    event.preventDefault();
    $(this).parent().fadeOut();
    $(this).siblings().find('input').val("");
});

function isCorrect(inputs) {
    var form = $(inputs).parent();
    var correctness = true;

    inputs.each(function () {
        if ($(this).val() == "")
            correctness = false;

    });


    var title = form.find('.title').val();
    var author = form.find('.author').val();
    var publisher = form.find('.publisher').val();
    var isbn = form.find('.isbn').val();

    if (!TITLE_AND_PUBLISHER_REGEX.test(title)) {
        alert('Incorrect title');
        correctness = false;
    } else if (!TITLE_AND_PUBLISHER_REGEX.test(publisher)) {
        alert('Incorrect publisher');
        correctness = false;
    } else if (!AUTHOR_REGEX.test(author)) {
        alert('Incorrect author');
        correctness = false;
    } else if (!ISBN_REGEX.test(isbn)) {
        alert('Incorrect isbn');
        correctness = false;
    }

    return correctness;
}

$('body').on('click', '#add-button', function (event) {
    event.preventDefault();
    var inputs = $(this).parent().find('input');

    if (isCorrect(inputs)) {

        var book = {
            title: $('#addBook .title').val(),
            author: $('#addBook .author').val(),
            publisher: $('#addBook .publisher').val(),
            isbn: $('#addBook .isbn').val(),
            type: $('#addBook .type').val()
        };

        AJAX('POST', book).done(function (newBook) {
            var duplicate = $('#display-books .book').find('h3[data-id="' + newBook.id + '"]');
            if (duplicate.length == 0) {
                var newDiv = $('<div class="book">' +
                    '<h3 data-id="' + newBook.id + '" class="book-title">' + newBook.title + '</h3>' +
                    '<div class="book-details">' +
                    '</div>' +
                    '</div>');

                $('#books').append(newDiv);
                $('#addBook input').val('');
                $('#addBook').fadeOut();
            } else {
                alert("The book with this isbn subscriber already exists in the database");
                $('#addBook input.isbn').val('');
            }

        })
    }

});

$('body').on('click', '#edit-button', function (event) {
    event.preventDefault();
    var inputs = $(this).parent().find('input');

    if (isCorrect(inputs)) {

        var book = {
            id: bookToUpdateId,
            title: $('#editBook .title').val(),
            author: $('#editBook .author').val(),
            publisher: $('#editBook .publisher').val(),
            isbn: $('#editBook .isbn').val(),
            type: $('#editBook .type').val()
        };

        AJAX('PUT', book).done(function () {
            var book = $('h3[data-id="' + bookToUpdateId + '"]').parent();
            book.fadeOut(400);

            var title = book.find('h3');
            var isbn = title.next().children(':nth-child(1)');
            var author = title.next().children(':nth-child(2)');
            var publisher = title.next().children(':nth-child(3)');
            var type = title.next().children(':nth-child(4)');

            setTimeout(function () {
                AJAX('GET', bookToUpdateId).done(function (book) {
                    title.text(book.title);
                    isbn.text('ISBN: ' + book.isbn);
                    author.text('Author: ' + book.author);
                    publisher.text('Publisher: ' + book.publisher);
                    type.text('Type: ' + book.type);
                });
            }, 500);

            book.fadeIn(400);
            $('#editBook input').val('');
            $('#editBook').fadeOut();
        });
    }

});

$('body').on('click', '.delete', function (event) {
    if (confirm('Are you sure you want to delete the book?')) {

        AJAX('DELETE', event.target.dataset.id);

        var bookDiv = $(event.target).parent().parent();
        bookDiv.fadeOut(800);

        setTimeout(function () {
            bookDiv.remove();
        }, 800);
    }
});

// === START
$('.start-invisible').fadeOut(0).removeClass('start-invisible');
getBooks();



