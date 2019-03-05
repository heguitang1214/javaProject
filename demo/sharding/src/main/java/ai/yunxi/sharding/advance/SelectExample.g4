grammar SelectExample;
sql : 'select' WHAT 'from' tables ';';
WHAT : [a-z]+ ;
tables : WHAT;
WS : [ \t\r\n]+ -> skip ;