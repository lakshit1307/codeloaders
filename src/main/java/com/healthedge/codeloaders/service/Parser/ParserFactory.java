package com.healthedge.codeloaders.service.Parser;

public class ParserFactory {

    private static String codeType;
    private static ParserFactory parserFactoryInstance=new ParserFactory();

    public static ParserFactory getParserFactoryInstance(){ return parserFactoryInstance;}



    public Parser getParser(String codeType){
        if (codeType.equals("zip")){

            return new ZipParser();

        }
        else if(codeType.equals("zipToCarrierLocality")){
            return new ZipToCarrierLocalityParser();
        }
        else
        return null;
    }

}
