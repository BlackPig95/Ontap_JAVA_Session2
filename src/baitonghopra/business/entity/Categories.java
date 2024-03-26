package baitonghopra.business.entity;

import java.util.Scanner;

public class Categories
{
    private int catalogId;
    private static int maxId = 0;
    private String catalogName;
    private String description;
    private boolean catalogStatus;

    public Categories()
    {
        this.catalogId = ++maxId;//Id tự tăng
    }

    public Categories(String catalogName, String description, boolean catalogStatus)
    {
        this.catalogId = ++maxId;//Id tự tăng
        this.catalogName = catalogName;
        this.description = description;
        this.catalogStatus = catalogStatus;
    }

    public int getCatalogId()
    {
        return catalogId;
    }

    public void setCatalogId(int catalogId)
    {
        this.catalogId = catalogId;
    }

    public String getCatalogName()
    {
        return catalogName;
    }

    public void setCatalogName(String catalogName)
    {
        this.catalogName = catalogName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isCatalogStatus()
    {
        return catalogStatus;
    }

    public void setCatalogStatus(boolean catalogStatus)
    {
        this.catalogStatus = catalogStatus;
    }

    public void displayData()
    {
        System.out.printf("Mã danh mục: %d | Tên danh mục: %s | Mô tả danh mục: %s | Trạng thái danh mục: %s\n",
                this.catalogId, this.catalogName, this.description, this.catalogStatus ? "Hoạt động" : "Không hoạt động");
    }

    public void inputData(Scanner scanner, Categories[] categoriesArr, boolean isAdding)
    {
        inputName(scanner, categoriesArr, isAdding);
        System.out.println("Nhập mô tả danh mục");
        this.description = scanner.nextLine();
        inputStatus(scanner);
    }

    private void inputName(Scanner scanner, Categories[] categoriesArr, boolean isAdding)
    {
        while (true)
        {
            System.out.println("Nhập tên danh mục, tối đa 50 ký tự");
            String inputName = scanner.nextLine();
            if (inputName.isBlank())
            {
                System.out.println("Không được để trống tên danh mục");
            } else if (inputName.length() > 50)
            {
                System.out.println("Tên danh mục chỉ được có độ dài tối đa 50 ký tự");
            } else
            {
                boolean nameExisted = false;//flag để xét sự tồn tại
                for (int i = 0; i < categoriesArr.length; i++)
                {
                    if (categoriesArr[i] == null)//Tránh việc duyệt phần tử null
                        break;
                    if (inputName.equals(categoriesArr[i].catalogName))
                    {   //Cho phép đặt lại tên cũ nếu là hành động update
                        if (isAdding)
                        {
                            System.out.println("Tên danh mục đã tồn tại, vui lòng chọn tên khác");
                            nameExisted = true;
                        }
                        break;
                    }
                }
                if (!nameExisted)//flag false => không trùng lặp
                {
                    this.catalogName = inputName;
                    break;
                }
            }
        }
    }

    private void inputStatus(Scanner scanner)
    {
        while (true)
        {
            System.out.println("Nhập trạng thái danh mục: True = Hoạt động, False = Không hoạt động");
            String newStatus = scanner.nextLine();
            if (newStatus.equalsIgnoreCase("true") || newStatus.equalsIgnoreCase("false"))
            {
                this.catalogStatus = Boolean.parseBoolean(newStatus);
                break;
            } else
            {
                System.out.println("Vui lòng chỉ nhập true hoặc false");
            }
        }
    }
}
