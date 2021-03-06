package com.rxt.common.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 生成票卡信息
 */
public class genTCard {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/coco/cards.csv"));

        SnowFlakeUIDWorker idWorker = new SnowFlakeUIDWorker(0, 0);

        for (int i = 0; i < 12000000; i++) {
            String line = new TicketCard(Long.toHexString(idWorker.nextId()), RandomCardTypeWorker.genCardType()).toString();

            fileOutputStream.write(line.getBytes());
            if (i % 10000 == 0) {
                System.out.println("已生成 " + i + " 条");
                fileOutputStream.flush();
            }
        }

        fileOutputStream.close();
    }
}

/**
 * 票卡信息
 */
class TicketCard {
    private String uid;    //卡号
    private String type;   //卡类型：单程票，学生票，羊城通，老年卡，优待卡，一日票，三日票，二维码

    public TicketCard() {
    }

    public TicketCard(String uid, String type) {
        this.uid = uid;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public TicketCard setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getType() {
        return type;
    }

    public TicketCard setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return uid + "," + type + "\n";
    }

    @Override
    public int hashCode() {
        return this.getUid().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getUid().equals(((TicketCard) obj).getUid());
    }
}


