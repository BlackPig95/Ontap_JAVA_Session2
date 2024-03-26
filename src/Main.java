public class Main
{
    public static void main(String[] args)
    {
        int[] arr1 = new int[]{1, 2, 3};
        int[] arr2 = new int[]{4, 5, 6};
        System.out.println("Trước method");
        System.out.println("Arr1");
        System.out.println(System.identityHashCode(arr1));
        for (int i : arr1)
            System.out.print(i);
        System.out.println();
        System.out.println("Arr2");
        System.out.println(System.identityHashCode(arr2));
        for (int i : arr2)
            System.out.print(i);
        System.out.println();
        Test(arr1, arr2);
        System.out.println("Sau method");
        System.out.println(System.identityHashCode(arr1));
        for (int i : arr1)
            System.out.print(i);
    }

    public static void Test(int[] array1, int[] array2)
    {
        System.out.println("Trong method");
        System.out.println("Array1");
        System.out.println(System.identityHashCode(array1));
        for (int i : array1)
            System.out.print(i);
        System.out.println();
        System.out.println("Array2");
        System.out.println(System.identityHashCode(array2));
        for (int i : array2)
            System.out.print(i);
        System.out.println();
        System.out.println("Đặt lại");
        array1 = array2;
        System.out.println(System.identityHashCode(array1));
    }
}