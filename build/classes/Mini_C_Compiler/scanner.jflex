package Mini_C_Compiler;
import java_cup.runtime.Symbol;

%%
%state A
%cupsym simbolo
%class gramatilex
%cup
%public
%unicode
%line
%column
%char

Comentario ="/"[*] [^*]+ [*]"/" | "/"[*] [*]+ "/"
Comentario2 =[/][/] ([^\r\n])* \r|\n
characterval = '.'
stringval =[\"] [^\"\n]+ [\"\n]
floatval = [0-9]+ ("."[0-9]{1,7})?
numero =[0-9]+ 
letra =[a-zA-ZÑñ]+
iden ={letra}({letra}|{numero}|"_")*


%%

"StartVoodoo"      {return new Symbol(simbolo.startvoodoo, yycolumn, yyline);}
"EndVoodoo"        {return new Symbol(simbolo.endvoodoo, yycolumn, yyline);}
"EndRitual"        {return new Symbol(simbolo.endritual, yycolumn, yyline,new String(yytext()));}
"Spell"            {return new Symbol(simbolo.spell, yycolumn, yyline,new String(yytext()));}
"EndSpell"         {return new Symbol(simbolo.endspell, yycolumn, yyline,new String(yytext()));}
"return"           {return new Symbol(simbolo.treturn, yycolumn, yyline,new String(yytext()));}
"printf"            {return new Symbol(simbolo.printf, yycolumn, yyline,new String(yytext()));}
"scanf"             {return new Symbol(simbolo.scanf, yycolumn, yyline,new String(yytext()));}

/* VARIABLES */
"char"        {return new Symbol(simbolo.tchar, yycolumn, yyline,new String(yytext()));}
"int"          {return new Symbol(simbolo.tinteger, yycolumn, yyline,new String(yytext()));}
"float"            {return new Symbol(simbolo.tfloat, yycolumn, yyline,new String(yytext()));}
"string"           {return new Symbol(simbolo.tstring, yycolumn, yyline,new String(yytext()));}
"void"             {return new Symbol(simbolo.tvoid, yycolumn, yyline,new String(yytext()));}
 
"endwhile"         {return new Symbol(simbolo.tendwhile, yycolumn, yyline,new String(yytext()));}
"select"           {return new Symbol(simbolo.tselect, yycolumn, yyline,new String(yytext()));}
"endselect"        {return new Symbol(simbolo.tendselect, yycolumn, yyline,new String(yytext()));}
"option"           {return new Symbol(simbolo.toption, yycolumn, yyline,new String(yytext()));}
"endoption"        {return new Symbol(simbolo.tendoption, yycolumn, yyline,new String(yytext()));}
"default"          {return new Symbol(simbolo.tdefault, yycolumn, yyline,new String(yytext()));}
"enddefault"       {return new Symbol(simbolo.tenddefault, yycolumn, yyline,new String(yytext()));}

"if"               {return new Symbol(simbolo.tif, yycolumn,yyline,new String(yytext())); }
"then"             {return new Symbol(simbolo.tthen, yycolumn,yyline,new String(yytext())); }
"else"             {return new Symbol(simbolo.telse, yycolumn,yyline,new String(yytext())); }
"endif"            {return new Symbol(simbolo.tendif, yycolumn,yyline,new String(yytext())); }
"while"            {return new Symbol(simbolo.twhile, yycolumn,yyline,new String(yytext())); }
"for"              {return new Symbol(simbolo.tfor, yycolumn,yyline,new String(yytext())); }
"endfor"           {return new Symbol(simbolo.tendfor, yycolumn,yyline,new String(yytext())); }
[<][!]"inicio"     {yybegin(A); return new Symbol(simbolo.tstart, yycolumn,yyline,new String(yytext())); }

/* OPERADOR */
"="                {return new Symbol(simbolo.assign, yycolumn,yyline,new String(yytext())); }
"+"                {return new Symbol(simbolo.mas, yycolumn,yyline,new String(yytext())); }
"-"                {return new Symbol(simbolo.menos, yycolumn,yyline,new String(yytext())); }
";"                {return new Symbol(simbolo.coma, yycolumn,yyline,new String(yytext())); }
","                {return new Symbol(simbolo.tseparator, yycolumn,yyline,new String(yytext())); }
":"                {return new Symbol(simbolo.dospuntos, yycolumn,yyline,new String(yytext())); }
">="               {return new Symbol(simbolo.greaterorequal, yycolumn,yyline,new String(yytext())); }
"<="               {return new Symbol(simbolo.lessorequal, yycolumn,yyline,new String(yytext())); }
"&&"              {return new Symbol(simbolo.tand, yycolumn,yyline,new String(yytext())); }
"and"              {return new Symbol(simbolo.tand, yycolumn,yyline,new String(yytext())); }
"||"               {return new Symbol(simbolo.tor, yycolumn,yyline,new String(yytext())); }
"or"               {return new Symbol(simbolo.tor, yycolumn,yyline,new String(yytext())); }
"not"              {return new Symbol(simbolo.tnot, yycolumn,yyline,new String(yytext())); }
"!="              {return new Symbol(simbolo.tnotequal, yycolumn,yyline,new String(yytext())); }
"!"              {return new Symbol(simbolo.tnot, yycolumn,yyline,new String(yytext())); }
"=="               {return new Symbol(simbolo.tequal, yycolumn,yyline,new String(yytext())); }
"*"                {return new Symbol(simbolo.por, yycolumn,yyline,new String(yytext())); }
"/"                {return new Symbol(simbolo.division, yycolumn,yyline,new String(yytext())); }
">"                {return new Symbol(simbolo.greater, yycolumn,yyline,new String(yytext())); }
"<"                {return new Symbol(simbolo.less, yycolumn,yyline,new String(yytext())); }
"("                {return new Symbol(simbolo.parizq, yycolumn,yyline,new String(yytext())); }
")"                {return new Symbol(simbolo.parder, yycolumn,yyline,new String(yytext())); }
"{"                {return new Symbol(simbolo.llaveizq, yycolumn,yyline,new String(yytext())); }
"}"                {return new Symbol(simbolo.llaveder, yycolumn,yyline,new String(yytext())); }
"["                {return new Symbol(simbolo.sbracketizq, yycolumn,yyline,new String(yytext())); }
"]"                {return new Symbol(simbolo.sbracketder, yycolumn,yyline,new String(yytext())); }
"&"                {return new Symbol(simbolo.amp, yycolumn,yyline,new String(yytext())); }


/*String*/

{stringval}          {return new Symbol(simbolo.stringval, yycolumn,yyline,new String(yytext()));}
{numero}           {return new Symbol(simbolo.numero, yycolumn,yyline,new String(yytext()));}
{iden}             {return new Symbol(simbolo.iden, yycolumn,yyline,new String(yytext()));}
{characterval}   {return new Symbol(simbolo.characterval, yycolumn,yyline,new String(yytext()));}
{floatval}          {return new Symbol(simbolo.floatval, yycolumn,yyline,new String(yytext()));}

/* COMENTARIOS */
{Comentario}       { /* Se ignoran */}
{Comentario2}      { /* Se ignoran */}

/* BLANCOS */
[ \t\r\f\n]+       { /* Se ignoran */}

<A>{
{numero}           {return new Symbol(simbolo.numero, yycolumn,yyline,new String(yytext()));}
";"                {return new Symbol(simbolo.coma, yycolumn,yyline,new String(yytext())); }
[!][>]             {yybegin(YYINITIAL); return new Symbol(simbolo.tend, yycolumn,yyline);}
[ \t\r\f\n]+       { /* Se ignoran */}
.                  { System.out.println("Error lexico dentro de <!inicio: "+yytext()); }
} 

/* Cualquier Otro */
.   { System.out.println("Error lexico, No se encuentra simbolo: "+yytext()+". En la linea: "+(yyline+1)+" en la columna: "+(yycolumn+1)); }
