#!/bin/bash
nohup /usr/bin/java -jar /home/ec2-user/returns-1.0.jar --spring.config.name=/home/ec2-user/lymm.application.properties & > /home/ec2-user/nohup.out