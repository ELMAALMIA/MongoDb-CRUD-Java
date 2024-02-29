package models;

import org.bson.types.ObjectId;

import java.util.List;

public class Book {
        private ObjectId id;
        private String title;
        private int publicationYear;
        private List<ObjectId> authorIds; // Liste d'auteurs pour ce livre

    public Book() {

    }

    public ObjectId getId() {
            return id;
        }

        public void setId(ObjectId id) {
            this.id = id;
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

        public List<ObjectId> getAuthorIds() {
            return authorIds;
        }

        public void setAuthorIds(List<ObjectId> authorIds) {
            this.authorIds = authorIds;
        }
    }


