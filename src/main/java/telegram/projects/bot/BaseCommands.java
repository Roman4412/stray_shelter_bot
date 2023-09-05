package telegram.projects.bot;
public enum BaseCommands {
    START("/start","Приветствие"),
    CATS("/cats","О приюте для кошек"),
    DOGS("/dogs","О приют для собак"),
    TAKE_PET("/take_pet","Как взять питомца"),
    REPORT("/report","Отправить отчет"),
    VOLUNTEER("/volunteer","Повать волонтера"),
    ABOUT_US("/abouts_as","О нашем приюте"),
    CONTACTS("/contacts","Контакты"),
    SAFETY("/safety","Техника безопасности"),
    CANCEL("/cancel ","Техника безопасности");
    private final String botCmd;
    private final String content;
    BaseCommands(String botCmd, String content) {
        this.botCmd=botCmd;
        this.content = content;
    }


    public String getCommand() {
        return botCmd;
    }
    public String getContent() {
        return content;
    }
}
