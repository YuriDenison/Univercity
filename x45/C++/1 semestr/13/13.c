#include<stdio.h>
#include<stdlib.h>
#include<math.h>

typedef struct Elem
{
	int a;
	int n;
	int l;
}Elem;

int len(int b)
{
	float f;
	int j=1;
	if (b<0)
	{
		b*= -1;
	}
	f=b/10;
	while (f>1){
		f/=10;
		j++;
	}
	return j;
}


int main()
{
	int n, i, j, k=0;
	Elem *arr;
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
	scanf("%d", &n);
	arr = (Elem*)malloc((n+1)*sizeof(Elem));
	for(i = n; i >= 0; i--)
	{
		scanf("%d", &arr[i].a);
		arr[i].n=i;
	}
	// Ликвидация нулей

	for(i = n; i >= 0; i--)
	{
		if(arr[i].a == 0 && i>0)
		{
			for(j = i; j>=0; j--)
			{
				arr[j].a = arr[j-1].a ;
				arr[j].n = arr[j-1].n ;
			}
			k++;
		}
		
	}
	// заполнения поля отступов
	for(i = n; i >= k; i--)
	{
		arr[i].l=len(i)+len(arr[i].a)+1;
	}
	
	// вывод степеней
	for(i = n; i >= k; i--)
	{
		if (arr[i].n > 0)
		{
			for (j=0; j<=(arr[i].l - len(arr[i].n));j++)
				printf(" ");
			printf("%d", arr[i].n);
		}
	}
	printf("\n");
	// вывод полинома
	if(arr[n].a > 0)
		printf(" ");
	for(i = n; i >= k; i--)
	{
		if(arr[i-1].a >0)
		{
			printf("%dx", arr[i].a);
			for (j=0; j<=(arr[i].l - len(arr[i].a - 1));j++)
				printf(" ");
			printf("+");
		}
		if(arr[i-1].a <0)
		{
			printf("%dx", arr[i].a);
			for (j=0; j<=(arr[i].l - len(arr[i].a - 1));j++)
				printf(" ");
			printf("-");
			arr[i-1].a = (-1)*arr[i-1].a ;
		}
		
	}
	printf("%d", arr[k-1].a);
	printf("\n");
	
	return 0;
}

