SplashActivity
Activity: SplashActivity
Start
    Declarations: btnStart, btnExit
    
    output "Screen Smart App"
    output "Your Name"
    output "Your Student Number"
    
    btnStart.setOnClickListener
        navigate to MainActivity
    end btnStart.setOnClickListener
    
    btnExit.setOnClickListener
        exit application
    end btnExit.setOnClickListener
Stop






MainActivity
Activity: MainActivity
Start
    Declarations: 
        String day
        num morningTime
        num afternoonTime
        List<String> days = []
        List<num> morningScreenTimes = []
        List<num> afternoonScreenTimes = []
        btnAdd, btnViewDetails, btnClear
    output "Enter Day"
    input day
    output "Enter Morning Screen Time (minutes)"
    input morningTime
    output "Enter Afternoon Screen Time (minutes)"
    input afternoonTime
    btnAdd.setOnClickListener
        if day is not empty and morningTime is valid and afternoonTime is valid then
            add day to days
            add morningTime to morningScreenTimes
            add afternoonTime to afternoonScreenTimes
            output "Data added"
        else
            output "Please enter all values"
        endif
    end btnAdd.setOnClickListener
    btnViewDetails.setOnClickListener
        navigate to DetailedViewActivity with days, morningScreenTimes, afternoonScreenTimes
    end btnViewDetails.setOnClickListener
    btnClear.setOnClickListener
        clear days
        clear morningScreenTimes
        clear afternoonScreenTimes
        output "Data cleared"
    end btnClear.setOnClickListener
Stop








DetailedViewActivity
Activity: DetailedViewActivity
Start
    Declarations:
        List<String> days
        List<num> morningScreenTimes
        List<num> afternoonScreenTimes
        num totalScreenTime = 0
        num averageScreenTime
    receive days, morningScreenTimes, afternoonScreenTimes from MainActivity
    for i from 0 to length of days - 1
        totalScreenTime = totalScreenTime + morningScreenTimes[i] + afternoonScreenTimes[i]
        output "Day: " + days[i]
        output "Morning: " + morningScreenTimes[i] + " mins"
        output "Afternoon: " + afternoonScreenTimes[i] + " mins"
    end for
    if length of days > 0 then
        averageScreenTime = totalScreenTime / length of days
    else
        averageScreenTime = 0
    endif
    output "Average Screen Time: " + averageScreenTime + " mins"
Stop

 
Here are the screenshots of my app
![Screenshot (1)](https://github.com/coherenceT/SplashActivity/assets/161321318/a56df826-a504-4f0a-9029-12ae5445dc6d)
