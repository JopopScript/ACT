import serial
import pymysql.cursors


def loar_receiver():

    # UART 세팅
    ser = serial.Serial(port='COM3', baudrate=115200, timeout=None)

    try:
        data = ser.readline().decode('utf8').replace(' ', '').replace('\n', '') #필요 옵션 추가
        data = data.upper()
        print('포트에서 받아온 온도값: ' + data)

    except Exception as e:
        pass

    # connection 정보

    conn = pymysql.connect(
        host='35.221.83.148',
        user="kopo22",
        password="kopo22",
        database="act",
        charset = 'utf8'
    )
    mycursor = conn.cursor()

    mycursor.execute("SELECT * FROM act_data")
    myresult = mycursor.fetchall()
    # 마지막으로 입력된 데이터에 온도값을 넣어주면 되니까 전체 데이터의 개수 셈
    # 튜플로 받아진 데이터들의 길이를 number라는 변수에 넣음
    number = len(myresult)

    data = float(data)
    #나중에 포트를 통해 받은 온도값을 35.6대신에 넣어주면 됨

    if data > 37.5:
        sql = "update act_data set temperature='%.2f',open=false where id=%d && temperature=0.00" % (data, number)
    else:
        sql = "update act_data set temperature='%.2f',open=true where id=%d && temperature=0.00" % (data, number)

    print('입력된 mysql 쿼리문: ' + sql)
    mycursor.execute(sql)
    conn.commit()

if __name__ == "__main__":
    while(True):
        loar_receiver()




