<!DOCTYPE html>

<html>
    
    <head>
        
    </head>
    <body>
        <h1>Klodin's EDH Scale</h1>
        <form action="EDHScale.php" method="post">
            <textarea name="decklist" rows="40" cols="35"></textarea>
            <br />
            <input type="submit" value="Evaluate Deck"/>
        </form>
        <?php ?>
        <? echo 5; ?>
        <?
        echo 5;
        if (exists($_POST['decklist']))
            echo "Wheeeeeeeeee!";
        $decklist = $_POST['decklist'];
        $deck = array();
        foreach ($decklist as $row) {
            if (!strpos($row, ' '))
                //throw new Exception("Illegal Deck");
                echo 'ILLEGAL DECK';
            $deck[] = 1;
        }
        $duallands = ['Badlands', 'Taiga', 'Savannah', 'Tundra', 'Underground Sea', 'Tropical Island', 'Volcanic Island', 'Plateau', 'Scrubland', 'Bayou']
        ?>
    </body>
</html>