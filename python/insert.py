import pymysql.cursors

def insert_data():
    print('hihi')
    # connection 정보

    conn = pymysql.connect(
        host='35.221.83.148',
        user="kopo22",
        password="kopo22",
        database="act"
    )
    mycursor = conn.cursor()

    mycursor.execute("SELECT * FROM act_data")
    myresult = mycursor.fetchall()
    # 마지막으로 입력된 데이터에 온도값을 넣어주면 되니까 전체 데이터의 개수 셈
    # 튜플로 받아진 데이터들의 길이를 number라는 변수에 넣음
    number = len(myresult)

    #나중에 포트를 통해 받은 온도값을 35.6대신에 넣어주면 됨
    sql = "update act_data set temperature='35.6' where id=%d " % (number)
    print(sql)
    mycursor.execute(sql)
    conn.commit()


insert_data()