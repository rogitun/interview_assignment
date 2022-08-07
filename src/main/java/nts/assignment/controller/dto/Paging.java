package nts.assignment.controller.dto;

import lombok.Data;

@Data
public class Paging {

    int first;
    int end;
    int totalPages;
    int number;

    public Paging(int totalPages, int number) {
        this.totalPages = totalPages;
        this.number = number;

        this.first = (int) Math.floor(number / 10) * 10 + 1;
        this.end = Math.min(first + 9, totalPages);
    }
}
