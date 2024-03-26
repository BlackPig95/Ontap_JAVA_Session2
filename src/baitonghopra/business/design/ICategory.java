package baitonghopra.business.design;

import baitonghopra.business.entity.Categories;

import java.util.Scanner;

public interface ICategory extends IManageable
{
    void updateStatus(Scanner scanner);//Cập nhật trạng thái cho danh mục

}
