package com.example.wolny.Presenter;

import android.content.Context;

import com.example.wolny.IMain;
import com.example.wolny.Model.Quote;
import com.example.wolny.Room.Dao.QuoteDatabase;

import java.util.ArrayList;

public class SetQuotePresenter {
    IMain.ISetQuote iSetQuote;

    public SetQuotePresenter(IMain.ISetQuote iSetQuote) {
        this.iSetQuote = iSetQuote;
    }

    public void getQuote(Context context){
        Quote quote = QuoteDatabase.getInstance(context).quoteDao().getRandomQuote();

        iSetQuote.setQuote(quote.getAuthor(), quote.getContent());
    }

    public void insertQuote(Context context){
        Quote quote = new Quote("Alexander Graham Bell","Concentrate all your thoughts upon the work in hand. The sun's rays do not burn until brought to a focus.   ");
        Quote quote1 = new Quote("Jim Rohn", "Either you run the day or the day runs you.");
        Quote quote2 = new Quote("Thomas Jefferson", "I’m a greater believer in luck, and I find the harder I work the more I have of it.");
        Quote quote3 = new Quote("Paulo Coelho", "When we strive to become better than we are, everything around us becomes better too.");
        Quote quote4 = new Quote("Thomas Edison", "Opportunity is missed by most people because it is dressed in overalls and looks like work.");

        QuoteDatabase quoteDatabase = QuoteDatabase.getInstance(context);

        quoteDatabase.quoteDao().insertQuote(quote);
        quoteDatabase.quoteDao().insertQuote(quote1);
        quoteDatabase.quoteDao().insertQuote(quote2);
        quoteDatabase.quoteDao().insertQuote(quote3);
        quoteDatabase.quoteDao().insertQuote(quote4);
    }
}
